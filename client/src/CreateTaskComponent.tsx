import React, {Component} from "react"
import {Box, Button, TextField} from "@material-ui/core"
import SaveIcon from '@material-ui/icons/Save';
import {NewTask, TodoAppService} from "./TodoAppService"

interface Props {
    todoAppService: TodoAppService
    refreshTasks: () => void
}


interface State {
    titleInput: string
    descriptionInput: string
}

export default class CreateTaskComponent extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            titleInput: "",
            descriptionInput: "",
        }
    }

    private async onClickSaveButton() {
        this.props.todoAppService.createTask(new NewTask(this.state.titleInput, this.state.descriptionInput)).then(() => {
            this.props.refreshTasks()
        })
        this.setState({titleInput: "", descriptionInput: ""})
    }

    public render() {
        return (
            <Box position="sticky" flexDirection="row" mt={2} mb={2} display="flex" justifyContent="space-between"
                 alignItems="flex-end">
                <Box flexGrow={1} marginRight={5}>
                    <TextField fullWidth id="taskNameInput" label="TaskName"
                               onChange={(event) => this.setState({titleInput: event.target.value})}
                               value={this.state.titleInput}/>
                    <TextField fullWidth id="taskDescriptionInput" label="Description"
                               onChange={(event) => this.setState({descriptionInput: event.target.value})}
                               value={this.state.descriptionInput}/>
                </Box>
                <Button variant="contained" color="primary" size="large" startIcon={<SaveIcon/>}
                        onClick={this.onClickSaveButton.bind(this)}>
                    Save
                </Button>
            </Box>
        )
    }
}