import { Container, Segment } from "semantic-ui-react";

import TodoAdd from "./features/todo/TodoAdd.component";
import TodoList from "./features/todo/TodoList.component";

function App() {
  return (
    <Container>
      <Segment>
        <TodoAdd />
      </Segment>
      <TodoList />
    </Container>
  );
}

export default App;
