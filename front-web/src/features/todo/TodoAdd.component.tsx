import { useState } from "react";
import { Button, Form, InputOnChangeData } from "semantic-ui-react";
import { useAppDispatch } from "src/store";
import { addTodo } from "./todo.state";

export const TodoAdd = () => {
  const { todoText, onTodoTextChange, submitNewTodo } = useTodoAddLogic();
  return (
    <Form>
      <Form.Group>
        <Form.Input
          inline
          placeholder="What should be done?"
          value={todoText}
          onChange={onTodoTextChange}
        />
        <Button type="submit" onClick={submitNewTodo}>
          Submit
        </Button>
      </Form.Group>
    </Form>
  );
};

const useTodoAddLogic = () => {
  const [todoText, setTodoText] = useState<string>("");
  const dispatch = useAppDispatch();
  const onTodoTextChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    { value }: InputOnChangeData
  ) => setTodoText(value);
  const submitNewTodo = () => {
    dispatch(addTodo({ id: "3", text: todoText, done: false }));
    setTodoText("");
  };
  return { todoText, onTodoTextChange, submitNewTodo };
};

export default TodoAdd;
