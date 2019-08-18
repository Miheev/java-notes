export class TodoList {
  constructor() {
    /**
     *
     * @type {Array<{id: number, note: string}>}
     */
    this.list = [];
    this.nodeList = [];
    this.todoKey = 'App/Todo/List';
    this.rootElement = null;

  }

  noteChangeAction(event, nodeItem) {
    if (!event.target.value) {
      this.removeItem(nodeItem);
      return ;
    }
    if (!nodeItem.note) {
      this.addItem(nodeItem, event.target.value);
      return ;
    }

    this.changeItem(nodeItem, event.target.value);
  }

  createListItem(nodeItem) {
    let container = document.createElement('li');
    let text = document.createElement('textarea');

    text.placeholder = 'Place notes here';
    text.rows = 5;
    text.value = nodeItem.note;

    text.addEventListener('change', (event) => {
      this.noteChangeAction(event, nodeItem);
    });

    container.appendChild(text);

    return container;
  }

  createList() {
    let node;
    this.list.forEach((noteItem) => {
      node = this.createListItem(noteItem);
      this.nodeList.push(node);
    });
  }

  createContainer() {
    let appRoot = document.createElement('ul');
    appRoot.classList.add('todo-list');

    return appRoot;
  }

  async fillList(appRootElement) {
    this.rootElement = this.createContainer(appRootElement);
    this.list = await this.getList();

    if (this.list.length) {
      this.createList();
    }

    this.addEmptyNode();
    this.nodeList.forEach(node => {
      this.rootElement.appendChild(node);
    });

    appRootElement.appendChild(this.rootElement);
  }

  async getList() {
    let listString = localStorage.getItem(this.todoKey) || '[]';
    return JSON.parse(listString);
  }

  async saveList() {
    localStorage.setItem(this.todoKey, JSON.stringify(this.list.slice(0, -1)));
  }

  addEmptyNode() {
    let maxId = Math.max(...this.list.map(item => item.id));
    if (maxId < 0) {
      maxId = 0;
    }
    let emptyNode = {id: maxId + 1, note: ''};
    let emptyElement = this.createListItem(emptyNode);

    this.list.push(emptyNode);
    this.nodeList.push(emptyElement);

    return emptyElement;
  }

  addItem(nodeItem, value) {
    nodeItem.note = value;

    let node = this.addEmptyNode();
    this.rootElement.appendChild(node);

    this.saveList();
  }

  changeItem(nodeItem, value) {
    nodeItem.note = value;

    this.saveList();
  }

  removeItem(nodeItem) {
    let index = this.findListIndex(nodeItem);

    this.rootElement.removeChild(this.nodeList[index]);

    this.list = this.list.filter(item => item !== nodeItem);
    this.nodeList = this.nodeList.filter((item, nodeIndex) => {
      return nodeIndex !== index;
    });

    this.saveList();
  }

  findListIndex(nodeItem) {
    let index = this.list.indexOf(nodeItem);
    if (index === -1) {
      throw new Error('item in list not found');
    }

    return index;
  }
}
