import { useSelector } from "react-redux";
import { Checkbox, Form, Segment } from "semantic-ui-react";
import { useAppDispatch } from "src/store";
import { setDone, Todo, todoListSelector } from "./todo.state";

export const TodoList = () => {
  const todoList = useSelector(todoListSelector);
  return (
    <Form>
      {todoList.map((item, index) => (
        <TodoListItem
          key={item.id}
          todo={item}
          isFirst={index === 0}
          isLast={index === todoList.length - 1}
        />
      ))}
    </Form>
  );
};

interface TodoListItemProps {
  todo: Todo;
  isFirst: boolean;
  isLast: boolean;
}

const TodoListItem = ({ todo, isFirst, isLast }: TodoListItemProps) => {
  const dispatch = useAppDispatch();
  const setChecked = (checked?: boolean) => {
    if (checked !== undefined && checked !== todo.done)
      dispatch(setDone({ id: todo.id, done: checked }));
  };
  return (
    <Segment attached={isFirst ? "top" : isLast ? "bottom" : true}>
      <Form.Field>
        <Checkbox
          label={todo.text}
          checked={todo.done}
          onChange={(e, data) => setChecked(data.checked)}
        />
      </Form.Field>
    </Segment>
  );
};

export default TodoList;
