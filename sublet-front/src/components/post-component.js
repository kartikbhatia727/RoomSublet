import React, { Component } from "react";
import userService from "../services/user-service";

export default class Post extends Component {
    constructor(props) {
      super(props);
      this.onChangeTitle = this.onChangeTitle.bind(this);
      this.onChangeDescription = this.onChangeDescription.bind(this);
      this.getPost = this.getPost.bind(this);
      // this.updatePublished = this.updatePublished.bind(this);
      this.updatePost = this.updatePost.bind(this);
  
      this.state = {
        currentPost: {
          postid: null,
          title: "",
          description: ""
        },
        message: ""
      };
    }

    componentDidMount() {
        this.getPost(35,this.props.match.params.id);
      }

      onChangeTitle(e) {
        const title = e.target.value;
    
        this.setState(function(prevState) {
          return {
            currentPost: {
              ...prevState.currentPost,
              title: title
            }
          };
        });
      }
    
      onChangeDescription(e) {
        const description = e.target.value;
        
        this.setState(prevState => ({
          currentPost: {
            ...prevState.currentPost,
            description: description
          }
        }));
      }

      getPost(user_id,id) {
        userService.getUserPostById(user_id,id)
          .then(response => {
            this.setState({
              currentPost: response.data
            });
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
      }

    //   updatePublished(status) {
    //     var data = {
    //       id: this.state.currentPost.id,
    //       title: this.state.currentPost.title,
    //       description: this.state.currentPost.description
    //     };
    
    //     userService.updatePost(this.state.currentTutorial.id, data)
    //       .then(response => {
    //         this.setState(prevState => ({
    //           currentPost: {
    //             ...prevState.currentTutorial
    //           }
    //         }));
    //         console.log(response.data);
    //       })
    //       .catch(e => {
    //         console.log(e);
    //       });
    //   }


      updatePost() {
        userService.updatePost(
          this.state.currentPost.postid,
          this.state.currentPost
        )
          .then(response => {
            console.log(response.data);
            this.setState({
              message: "The Post was updated successfully!"
            });
          })
          .catch(e => {
            console.log(e);
          });
      }
      
      render()
      {
          const {currentPost} = this.state;
          return (

            <div>
        {currentPost &&
          <div className="edit-form">
            <h4>Post</h4>
            <form>
              <div className="form-group">
                <label htmlFor="title">Title</label>
                <input
                  type="text"
                  className="form-control"
                  id="title"
                  value={currentPost.title}
                  onChange={this.onChangeTitle}
                />
              </div>
              <div className="form-group">
                <label htmlFor="description">Description</label>
                <input
                  type="text"
                  className="form-control"
                  id="description"
                  value={currentPost.description}
                  onChange={this.onChangeDescription}
                />
              </div>
              </form>

              <button
              type="submit"
              className="badge badge-primary"
              onClick={this.updatePost}
            >
              Update
            </button>
            <p>{this.state.message}</p>
        
          </div>
      }
</div>
          );

      }
    }
