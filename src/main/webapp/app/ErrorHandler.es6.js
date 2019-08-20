const TIMEOUT = 6000;

let timerHandler = undefined;
let errorElement = null;

function releaseLock() {
  if (timerHandler !== undefined) {
    clearTimeout(timerHandler);
  }
  timerHandler = undefined;
}

function obtainLock() {
  if (timerHandler !== undefined) {
    releaseLock()
  }

  timerHandler = setTimeout(() => {
    if (errorElement.classList.contains('is-visible')) {
      errorElement.classList.remove('is-visible');
    }
    timerHandler = undefined;
  }, TIMEOUT);
}

function toggleVisibility() {
  if (!errorElement.classList.contains('is-visible')) {
    errorElement.classList.add('is-visible');
  }
}

function outHttpError(httpError) {
  errorElement.textContent = `${httpError.error}: ${httpError.message}.\nTry again or contact our support, please.`;
  toggleVisibility();
}

function outError(httpError) {
  errorElement.textContent = `${String(httpError)}.\nTry again or contact our support, please.`;
  toggleVisibility();
}

export const ErrorHandler = {
  destroy() {
    releaseLock();
    errorElement = null;
  },
  linkElement(htmlElement) {
    errorElement = htmlElement;
  },
  logError(httpError) {
    releaseLock();

    let isError = httpError instanceof Error;
    if (isError && httpError.data) {
      outHttpError(httpError.data);
    } else if (isError) {
      outError(httpError);
    } else {
      outHttpError(httpError);
    }

    obtainLock();
  },
};
