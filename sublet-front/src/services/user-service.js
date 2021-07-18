import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/';

class UserService {
  getPublicContent() {
    // all posts
    return axios.get(API_URL + 'posts');
  }

  findByPostslocation(loc)
  { //get post by location
    return axios.get(API_URL + `public/posts?location=${loc}`); //?location=${loc}`
  }
  // getUserBoard() {
  //   return axios.get(API_URL + 'user', { headers: authHeader() });
  // }

  getUserPosts(id)
   { //  get posts of user
    console.log("called");
    return axios.get(API_URL +`${id}/posts` ,{ headers: authHeader() });
  }

  getUserPostById(id,post_id)
  { //  get posts of user
   return axios.get(API_URL +`${id}`+"/posts/"+`${post_id}` , { headers: authHeader() });
 }


  createPost(id,data)
  {//save user post
    return axios.post(API_URL + `${id}`+"/posts" , data , { headers: authHeader() });
  }

  updatePost(post_id,data)
  {
    // update user post 
    return axios.put(API_URL + "posts/"+`${post_id}`, data ,{ headers: authHeader() });
  }

  deletePost(post_id)
  {
    return axios.delete(API_URL + `posts/${post_id}`, { headers: authHeader() });
  }

//   getModeratorBoard() {
//     return axios.get(API_URL + 'mod', { headers: authHeader() });
//   }

//   getAdminBoard() {
//     return axios.get(API_URL + 'admin', { headers: authHeader() });
//   }
}

export default new UserService();