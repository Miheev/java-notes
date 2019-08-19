/**
 * Notes App
 * - list notes
 * - CRUD notes
 * - CRUD without buttons, event subscription only
 * - REST API
 * - Browsers support: Chrome (tested and developed here), Firefox
 */
import {TodoList} from './TodoList.es6.js';
import {ServerStore} from './store/ServerStore.es6.js';
// import {LocalStore} from './store/LocalStore.es6.js';

const app = new TodoList(new ServerStore());

document.addEventListener('DOMContentLoaded', () => {
  app.init(document.getElementById('app'));
});
