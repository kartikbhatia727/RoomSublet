import React, { Component } from "react";
import userService from "../services/user-service";

export default class AddPost extends Component{
constructor(props){
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeDescription = this.onChangeDescription.bind(this);
    this.onChangeLocation = this.onChangeLocation.bind(this);
    this.savePost = this.savePost.bind(this);

this.state ={
 id:null,
    title:"",
description :"",
location:"",
submitted :false
};
}


onChangeTitle(e)
{
    this.setState({
        title : e.target.value
    });
}

onChangeDescription(e) {
    this.setState({
      description: e.target.value
    });
  }

  onChangeLocation(e) {
    this.setState({
    location: e.target.value
    });
  }
  savePost() {
    var data = {
      title: this.state.title,
      description: this.state.description,
      location: this.state.location
    };

    userService.createPost(this.props.match.params.id, data)
    .then(response => {
        this.setState({
            id : response.data.id,
            title: response.data.title,
          description: response.data.description,
          location: response.data.location,
          submitted:true

        });
        console.log(response.data);
    })
    .catch(e =>{
        console.log(e);
    });
  }

    newPost()
    {
        this.setState({
            id: null,
      title: "",
      description: "",
      location: "",
      submitted:false
        });
    }

    render()
    { 
      return(
<div className="submit-form">
    {this.state.submitted ? (
        <div>
        <h4>You submitted successfully!</h4>
      </div>
    ):(
        <div>
            <div className="form-group">
                <label htmlFor="title">Title</label>
                <input type="text" className="form-control" id="title" onChange={this.onChangeTitle} value={this.state.title} required name="tile" />
            </div>
            <div className="form-group">
                <label htmlFor="description">Description</label>
                <input type="text" className="form-control" id="description" onChange={this.onChangeDescription} value={this.state.description} required name="description" />
            </div>
            <div className="form-group">
                <label htmlFor="location">Location</label>
                <input type="text" className="form-control" id="location" onChange={this.onChangeLocation} value={this.state.location} required name="location" />
            </div>

            <button onClick={this.savePost} className="btn btn-success">
                Submit
            </button>

        </div>
    )}
</div>
      );
    }

}