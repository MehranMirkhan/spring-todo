import reducer, { addTodo, TodoState } from "./todo.state";

describe("Todo", () => {
  const initialState: TodoState = {
    todoList: [
      { id: "1", text: "Create a TODO Project", done: false },
      { id: "2", text: "Have fun ;)", done: true },
    ],
  };
  it("Should handle add", () => {
    const actual = reducer(
      initialState,
      addTodo({ id: "3", text: "Hello", done: false })
    );
    expect(actual.todoList.length).toEqual(3);
    expect(actual.todoList[2].text).toEqual("Hello");
  });
});
