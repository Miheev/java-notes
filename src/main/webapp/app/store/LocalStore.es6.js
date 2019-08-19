import {AbstractStore} from './AbstractStore.es6.js';

export class LocalStore extends AbstractStore {
  static ENDPOINT = 'App/Todo/List';

  async getAllNotes() {
    let listString = localStorage.getItem(LocalStore.ENDPOINT) || '[]';

    this.list = JSON.parse(listString);
    return this.list;
  }

  async createNote(noteDetails) {
    return this.saveList();
  }

  async updateNote(noteId, noteDetails) {
    return this.saveList();
  }

  async deleteNote(noteId) {
    return this.saveList();
  }

  async saveList() {
    localStorage.setItem(LocalStore.ENDPOINT, JSON.stringify(this.list.slice(0, -1)));
  }
}
