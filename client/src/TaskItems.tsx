import {IconButton, List, ListItem, ListItemSecondaryAction, ListItemText, Typography} from "@material-ui/core"
import DeleteIcon from '@material-ui/icons/Delete';
import React from "react"
import {Task, TodoAppService} from "./TodoAppService"

export interface Props {
    tasks: Task[]
    refreshTasks: () => void
    todoAppService: TodoAppService
}

const TaskItems: React.FC<Props> = (props) => {
    const onClickListItem = (task: Task) => {
        if (task.isDone) {
            props.todoAppService.revertTask(task.id).then(() => props.refreshTasks())
        } else {
            props.todoAppService.finishTask(task.id).then(() => props.refreshTasks())
        }
    }

    const onClickDeleteItem = (task: Task) => {
        props.todoAppService.deleteTask(task.id).then(() => props.refreshTasks())
    }

    return (
        <List>
            {props.tasks.map((task) => {
                return (
                    <ListItem key={task.id} button onClick={() => onClickListItem(task)}>
                        <ListItemText primary={
                            <Typography
                                style={{textDecoration: task.isDone ? "line-through" : "none"}}>{task.title}</Typography>}
                                      secondary={
                                          <Typography
                                              style={{textDecoration: task.isDone ? "line-through" : "none"}}>{task.description}</Typography>}/>
                        <ListItemSecondaryAction>
                            <IconButton edge="end" aria-label="delete" onClick={() => onClickDeleteItem(task)}>
                                <DeleteIcon/>
                            </IconButton>
                        </ListItemSecondaryAction>
                    </ListItem>
                )
            })}
        </List>
    )
}

export default TaskItems