import React from 'react';
import Header from "./Header"
import {Container} from "@material-ui/core"
import CreateTaskComponent from "./CreateTaskComponent"
import TaskItems from "./TaskItems"
import {Task, TodoAppService} from "./TodoAppService"


interface Props {
    todoAppService: TodoAppService
}

interface State {
    tasks: Task[]
}

export class App extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    async componentDidMount() {
        this.setState({tasks: await this.props.todoAppService.getAllTasks()})
    }

    private async refreshTasks() {
        this.setState({tasks: await this.props.todoAppService.getAllTasks()})
    }

    public render() {
        return (
            <div className="App">
                <Header/>
                <Container maxWidth="md">
                    <CreateTaskComponent todoAppService={this.props.todoAppService} refreshTasks={this.refreshTasks.bind(this)}/>
                    <TaskItems tasks={this.state.tasks} todoAppService={this.props.todoAppService} refreshTasks={this.refreshTasks.bind(this)}/>
                </Container>
            </div>
        )
    }
}

export default App;
