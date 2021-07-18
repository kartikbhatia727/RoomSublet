import React, { Component } from "react";

import UserService from "../services/user-service";

export default class PostLocation extends Component {
  constructor(props) {
    super(props);

    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.searchTitle = this.searchTitle.bind(this);

    this.state = {
      posts:[],
      searchTitle: "",
    };
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;

    this.setState({
      searchTitle: searchTitle
    });
  }

  searchTitle() {
    UserService.findByPostslocation(this.state.searchTitle)
      .then(response => {
        this.setState({
          posts: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }


  render() {
    const{posts} = this.state;
    return (
      <div className="list row">
        <div className="col-md-8">
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search by title"
              value={this.state.searchTitle}
              onChange={this.onChangeSearchTitle}
            />
            <div className="input-group-append">
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={this.searchTitle}
              >
                Search
              </button>
            </div>
          </div>
      </div>

      <div className="col-md-6">
          <h4>Posts List</h4>
          <ul className="list-group">
              {posts && posts.map(post => (
                    <li key= {post.id} className = "list-group-item ">
                      <div>
            <div>
            <label>
                  <strong>Title:</strong>
                </label>{" "}
                {post.title}
            </div>
            <div>
                <label>
                  <strong>Description:</strong>
                </label>{" "}
                {post.description}
              </div>
                </div>
               </li>
        
              ))}
              </ul>
    </div>
    </div>

    );
  }
}

