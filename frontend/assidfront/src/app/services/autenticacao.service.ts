import { Service } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginResponse //preparando o json e os campos da resposta
{
    accessToken: string,
    tokenType: string,
    expiresIn: number;
}

@Injectable({
    providedIn: 'root'
})

export class AutenticacaoService {
    private apiUrl = 'http://localhost:8085/api/auth'; //prepara a url base

    constructor(private http: HttpClient){} //cria o constructor para a requisicao http

    login(username: string,password: string): Observable<LoginResponse>{
        return this.http.post<LoginResponse>(
            `${this.apiUrl}/login`, //efetua a requisicao post em /login
            {username, password}
        )
    }
}
