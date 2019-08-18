/**
 * Notes App
 * - list notes
 * - CRUD notes
 * - CRUD without buttons, event subscription only
 * - REST API
 * - Browsers support: Chrome (tested and developed here), Firefox
 */
import {TodoList} from './TodoList.es6.js';

const app = new TodoList();

document.addEventListener('DOMContentLoaded', () => {
  app.fillList(document.getElementById('app'));
});
