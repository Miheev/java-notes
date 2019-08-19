import { AbstractStore } from './store/AbstractStore.es6.js';
import { ErrorHandler } from './ErrorHandler.es6.js';

export class TodoList {
  static NAME_LENGTH = 99;

  constructor(storeStrategy) {
    this.nodeList = [];
    this.containerElement = null;
    this.appRootElement = null;
    this.store = storeStrategy;

    if (!(storeStrategy instanceof AbstractStore)) {
      throw new Error("storeStrategy should implement of AbstractStore interface API");
    }
  }

  /**
   * List of notes
   * @type {Array<{id: number, name: string, description: string}>}
   */
  get list() {
    return this.store.list;
  }
  set list(newList) {
    this.store.list = newList;
  }

  init(appRootElement) {
    this.appRootElement = appRootElement;
    ErrorHandler.linkElement(appRootElement.querySelector('.error-handler'));

    appRootElement.addEventListener('remove', () => {
      this.destroy();
    });

    this.containerElement = this.createContainer();
    this.fillList();
  }

  destroy() {
    this.nodeList = [];
    this.containerElement = null;
    this.appRootElement = null;

    ErrorHandler.destroy();
    this.store.destroy();
    this.store = null;
  }

  async fillList() {
    await this.store.getAllNotes();

    if (this.list.length) {
      this.createList();
    }

    this.addEmptyNode();
    this.nodeList.forEach(node => {
      this.containerElement.appendChild(node);
    });

    this.appRootElement.appendChild(this.containerElement);
  }

  noteText(nodeItem) {
    if (nodeItem.name && nodeItem.description) {
      return nodeItem.name +'\n'+ nodeItem.description;
    }
    if (nodeItem.name) {
      return nodeItem.name;
    }
    if (nodeItem.description) {
      return nodeItem.description;
    }

    return '';
  }
  textToNote(nodeItem, value) {
    let newLineMatch = value.match(/[\n\r]/);
    if (value.length <= TodoList.NAME_LENGTH && !newLineMatch) {
      nodeItem.name = value;
      nodeItem.description = '';
      return ;
    }

    let nameEndIndex = newLineMatch && newLineMatch.index < TodoList.NAME_LENGTH
      ? newLineMatch.index
      : TodoList.NAME_LENGTH;
    nodeItem.name = value.slice(0, nameEndIndex);

    let textStartIndex = nameEndIndex;
    if (value[textStartIndex] === '\n') {
      textStartIndex += 1;
    } else if (value[textStartIndex] === '\r' && value[textStartIndex + 1] === '\n') {
      textStartIndex += 2;
    } else if (value[textStartIndex] === '\r') {
      textStartIndex += 1;
    }

    nodeItem.description = value.slice(textStartIndex);
  }


  createContainer() {
    let container = document.createElement('ul');
    container.classList.add('todo-list');
    return container;
  }
  addEmptyNode() {
    let maxId = Math.max(...this.list.map(item => item.id));
    if (maxId < 0) {
      maxId = 0;
    }
    let emptyNode = {id: maxId + 1, name: '', description: ''};
    let emptyElement = this.createListItem(emptyNode);

    this.list.push(emptyNode);
    this.nodeList.push(emptyElement);

    return emptyElement;
  }

  createList() {
    let node;
    this.list.forEach((noteItem) => {
      node = this.createListItem(noteItem);
      this.nodeList.push(node);
    });
  }
  createListItem(nodeItem) {
    let container = document.createElement('li');
    let text = document.createElement('textarea');

    text.placeholder = 'Place notes here';
    text.rows = 5;
    text.value = this.noteText(nodeItem);

    text.changeListener = (event) => {
      this.noteChangeAction(event, nodeItem);
    };
    text.addEventListener('change', text.changeListener);

    container.appendChild(text);

    return container;
  }

  noteChangeAction(event, nodeItem) {
    if (!event.target.value) {
      this.removeItem(nodeItem);
      return ;
    }
    if (!nodeItem.name) {
      this.addItem(nodeItem, event.target.value);
      return ;
    }

    this.changeItem(nodeItem, event.target.value);
  }
  async addItem(nodeItem, value) {
    this.textToNote(nodeItem, value);

    let node = this.addEmptyNode();
    this.containerElement.appendChild(node);

    let savedNote = await this.store.createNote(nodeItem);
    nodeItem.id = savedNote.id;
  }
  changeItem(nodeItem, value) {
    this.textToNote(nodeItem, value);

    this.store.updateNote(nodeItem.id, nodeItem);
  }
  removeItem(nodeItem) {
    let index = this.findListIndex(nodeItem);

    let textElement = this.nodeList[index].children[0];
    textElement.removeEventListener('change', textElement.changeListener);
    delete textElement.changeListener;
    this.containerElement.removeChild(this.nodeList[index]);

    this.list = this.list.filter(item => item !== nodeItem);
    this.nodeList = this.nodeList.filter((item, nodeIndex) => {
      return nodeIndex !== index;
    });

    this.store.deleteNote(nodeItem.id);
  }

  findListIndex(nodeItem) {
    let index = this.list.indexOf(nodeItem);
    if (index === -1) {
      throw new Error('item in list not found');
    }

    return index;
  }
}
