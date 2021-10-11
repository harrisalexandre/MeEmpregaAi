import axios from 'axios'
	export const http = axios.create({
		baseURL: 'https://meempregaai.herokuapp.com/empregado/'
	})