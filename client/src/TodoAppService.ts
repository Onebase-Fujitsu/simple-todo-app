import {FetchWrapper} from "./FetchWrapper"

export class Task {
    public id: number
    public title: string
    public description: string
    public isDone: boolean

    constructor(
        id: number,
        title: string,
        description: string,
        isDone: boolean
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.isDone = isDone
    }

    public static fromJson(taskJson: any) : Task {
        return new Task(taskJson.id, taskJson.title, taskJson.description, taskJson.isDone)
    }
}

export class NewTask {
    public readonly title: string
    public readonly description: string

    constructor(
        title: string,
        description: string
    ) {
        this.title = title
        this.description = description
    }
}

export interface TodoAppService {
    getAllTasks(): Promise<Task[]>
    createTask(newTask: NewTask): Promise<void>
    finishTask(id: number): Promise<void>
    revertTask(id: number): Promise<void>
    deleteTask(id: number): Promise<void>
}

export default class TodoAppServiceImpl implements TodoAppService {
    private fetchWrapper: FetchWrapper

    constructor(fetchWrapper: FetchWrapper) {
        this.fetchWrapper = fetchWrapper
    }

    async getAllTasks(): Promise<Task[]> {
        const response = await this.fetchWrapper.get("/tasks")
        return response.map(Task.fromJson)
    }

    async createTask(newTask: NewTask): Promise<void> {
        return await this.fetchWrapper.post("/tasks", newTask, "application/json")
    }

    async deleteTask(id: number): Promise<void> {
        return await this.fetchWrapper.delete(`/tasks/${id}`)
    }

    async finishTask(id: number): Promise<void> {
        return await this.fetchWrapper.put(`/tasks/${id}/finish`)
    }

    async revertTask(id: number): Promise<void> {
        return await this.fetchWrapper.put(`/tasks/${id}/revert`)
    }
}