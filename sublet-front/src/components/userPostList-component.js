import React, { Component } from "react";

import { Link } from "react-router-dom";
import userService from "../services/user-service";


export default class PostList extends Component{

constructor(props)
{
    super(props);
    this.retrievePosts = this.retrievePosts.bind(this);
    this.setActivePost = this.setActivePost.bind(this);
    this.deletePost = this.deletePost.bind(this);
    this.refreshList = this.refreshList.bind(this);


    this.state ={

        posts:[],
        currentPost: null,

    };
}


componentDidMount()
{
  this.retrievePosts(this.props.match.params.id);
}

retrievePosts(id)
{
console.log(id);
userService.getUserPosts(id)
.then(response =>
    {
        this.setState({
            posts:response.data
        });
        console.log(response.data);
    })
    .catch(e =>  {
        console.log(e);
    });
}

refreshList() {
    this.retrievePosts(this.props.match.params.id);
    this.setState({
      currentPost: null
    });
  }

deletePost()
{
userService.deletePost(this.state.currentPost.id)
.then(response => {
    console.log(response.data);
    this.props.history.push('/posts')
    this.refreshList();
})
.catch(e => {
    console.log(e);
});
}

setActivePost(post) {
    this.setState({
      currentPost: post
    });
  }

render()
{
    const{posts} =this.state;
return (
    <div className="list row">
    <div className="col-md-6">
          <h4>Posts List</h4>
          <ul className="list-group">
              {posts && posts.map(post => (
                    <li key={post.id }className = "list-group-item " onClick={() => this.setActivePost(post)}> {post.title}</li>
              ))}
              </ul>
    </div>

    <div className="col-md-6">
        {this.state.currentPost ?(
            <div>
            <h4>Post details</h4>
            <div>
            <label>
                  <strong>Title:</strong>
                </label>{" "}
                {this.state.currentPost.title}
            </div>
            <div>
                <label>
                  <strong>Description:</strong>
                </label>{" "}
                {this.state.currentPost.description}
              </div>
            
            <Link
                to={"/posts/" + this.state.currentPost.postid}>
                Edit
              </Link>


          <button
            className="m-3 btn btn-sm btn-danger"
            onClick={this.deletePost()}>
            Delete
          </button>
              </div>
              ) : (
                <div>
                  <br />
                  <p>Please click on a Post..</p>
                </div>
              )
              }
    </div>


</div>
);
}


}

