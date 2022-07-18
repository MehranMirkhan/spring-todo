import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "src/store";

export type Todo = {
  id: string;
  text: string;
  done: boolean;
};

export type TodoState = {
  todoList: Todo[];
};

const initialState: TodoState = {
  todoList: [
    { id: "1", text: "Create a TODO Project", done: false },
    { id: "2", text: "Have fun ;)", done: true },
  ],
};

export const todoSlice = createSlice({
  name: "todo",
  initialState,
  reducers: {
    addTodo: (state, action: PayloadAction<Todo>) => {
      const todo = action.payload;
      state.todoList.push(todo);
    },
    removeTodo: (state, action: PayloadAction<string>) => {
      const id = action.payload;
      state.todoList = state.todoList.filter((item) => item.id !== id);
    },
    setDone: (state, action: PayloadAction<{ id: string; done: boolean }>) => {
      const { id, done } = action.payload;
      state.todoList = state.todoList.map((item) =>
        item.id === id ? { ...item, done } : item
      );
    },
  },
});

export const { addTodo, removeTodo, setDone } = todoSlice.actions;

export const todoListSelector = (state: RootState) => state.todo.todoList;

export default todoSlice.reducer;
