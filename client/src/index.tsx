import React from 'react';
import ReactDOM from 'react-dom';
import "normalize.css";
import App from './App';
import reportWebVitals from './reportWebVitals';
import fetch from "isomorphic-fetch";
import FetchWrapperImpl from "./FetchWrapper"
import TodoAppServiceImpl from "./TodoAppService"

const fetchWrapper = new FetchWrapperImpl(fetch)

ReactDOM.render(
  <React.StrictMode>
    <App todoAppService={new TodoAppServiceImpl(fetchWrapper)} />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
