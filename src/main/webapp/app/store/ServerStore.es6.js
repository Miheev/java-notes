import {AbstractStore} from './AbstractStore.es6.js';
import { ErrorHandler } from '../ErrorHandler.es6.js';

async function sendRequest(url, method, body, defaultValue) {
  const options = {
    method: method,
    headers: {'Content-Type': 'application/json;charset=UTF-8'},
    mode: 'cors'
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  return fetch(url, options)
  .then(async response => {
    let result = await response.text();

    if (response.status > 399 && !result) {
      throw new Error("Unexpected request error");
    }

    return {response, result: JSON.parse(result)};
  })
  .then(({result, response}) => {
    if (result.error && result.message) {
      let error = new Error(`${result.error}: ${result.message}`);
      error.data = result;
      throw error;
    }
    return result;
  })
  .catch(error => {
    console.error(error);
    ErrorHandler.logError(error);
    return defaultValue;
  });
}

async function sendEmptyRequest(url, method) {
  const options = {
    method: method,
    headers: {'Content-Type': 'application/json;charset=UTF-8'},
    mode: 'cors'
  };

  return fetch(url, options)
  .then((response) => {
    if (response.status > 399) {
      throw new Error("Unexpected request error");
    }
    return response;
  })
  .catch(error => {
    console.error(error);
    ErrorHandler.logError(error);
  });
}

function resolveNoteUrl(noteId) {
  return ServerStore.ENDPOINT_ENTITY.replace('{noteId}', noteId);
}

export class ServerStore extends AbstractStore {
  static ENDPOINT = "/api/notes";
  static ENDPOINT_ENTITY = ServerStore.ENDPOINT + "/{noteId}";

  async getAllNotes() {
    this.list = await sendRequest(ServerStore.ENDPOINT, 'GET', undefined, []);
    return this.list;
  }

  async createNote(noteDetails) {
    return await sendRequest(ServerStore.ENDPOINT, 'POST', noteDetails, noteDetails);
  }

  async updateNote(noteId, noteDetails) {
    return await sendRequest(resolveNoteUrl(noteId), 'PUT', noteDetails, noteDetails);
  }

  async deleteNote(noteId) {
    return await sendEmptyRequest(resolveNoteUrl(noteId), 'DELETE');
  }
}
