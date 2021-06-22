type fetchSignature = (input: RequestInfo, init?: RequestInit) => Promise<Response>

export interface FetchWrapper {
    get(url: string): Promise<any>
    post(url: string, data?:any, contentType?:any): Promise<any>
    put(url: string, data?:any, contentType?:any): Promise<any>
    delete(url: string): Promise<any>
}

export default class FetchWrapperImpl implements FetchWrapper {
    private readonly fetch: fetchSignature
    constructor(fetch: fetchSignature) {
        this.fetch = fetch
    }

    async delete(url: string): Promise<any> {
        await fetch(url, {
            headers: FetchWrapperImpl.headers(),
            method: "DELETE",
        })
        return
    }

    async get(url: string): Promise<any> {
        const response = await fetch(url, {
            headers: FetchWrapperImpl.headers()
        })
        return await response.json()
    }

    async post(url: string, data?: any, contentType?: any): Promise<any> {
        data = contentType === "application/json" ? JSON.stringify(data) : data
        const response = await fetch(url, {
            body: data,
            headers: FetchWrapperImpl.headers(),
            method: "POST",
        })
        try {
            return await response.json()
        } catch (e) {
            return undefined
        }
    }

    async put(url: string, data?: any, contentType?: any): Promise<any> {
        data = contentType === "application/json" ? JSON.stringify(data) : data
        const response = await fetch(url, {
            body: data,
            headers: FetchWrapperImpl.headers(),
            method: "PUT",
        })
        try {
            return await response.json()
        } catch (e) {
            return undefined
        }
    }

    private static headers(contentType: string = "application/json") {
        return {
            "Content-Type": contentType,
        }
    }
}