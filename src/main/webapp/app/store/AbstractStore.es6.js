function notSupported() {
  throw new Error("Operation not supported. Method should be implemented in child class");
}

export class AbstractStore {
  constructor() {
    /**
     * List of notes
     * @type {Array<{id: number, name: string, description: string}>}
     */
    this.list = [];
  }

  async getAllNotes() { notSupported(); }

  async createNote(noteDetails) { notSupported(); }

  async getNote(noteId) { notSupported(); }

  async updateNote(noteId, noteDetails) { notSupported(); }

  async deleteNote(noteId) { notSupported(); }

  destroy() {
    this.list = [];
  }
}
