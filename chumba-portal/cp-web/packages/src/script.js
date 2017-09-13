/**
 * @author tc@vgw.co
 */


const Login = {
	template : "<div>Login</div>"
};
const Lobby = {
	template : "<div>Lobby</div>"
};

const routes = [
	{
		path : "/lobby",
		component : Lobby
	},
	{
		path : "/",
		component : Login
	}
];

const router = new VueRouter({
	routes // short for `routes: routes`
});

const app = new Vue({
	router
}).$mount('#app');	