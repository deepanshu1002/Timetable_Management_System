import "../css_file/rating.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { createUrl } from "../utils/utils";
import { toast } from "react-toastify";
import { Navigate,Route, Routes, useNavigate } from 'react-router-dom';


function CheckTeacherRatings()
{
    const[subjectId,setSubjectId] = useState('');
    const[subjects,setSubjects] = useState([]);
    const[selectedSubject,setSelectedSubject] = useState('');
    const[date,setDate] = useState('');
    const navigate = useNavigate()
    //const [teacherId,setTeacherId] = useState('');
    
    useEffect(() => {
       const teacherId =  sessionStorage['userId'];
        const url = createUrl(`/subject/get/${teacherId}`)
        axios.get(url)
          .then(response => {
            setSubjects(response.data);
          })
          .catch(error => {
            console.error('Error fetching subjects:', error);
          });
      }, []);
      const handleSubjectChange = (event) => {
        const newSelectedSubject = event.target.value;
        setSelectedSubject(newSelectedSubject);
        console.log(event.target.value)
        setSubjectId(newSelectedSubject)
      };

      const checkRatings = () =>
      {
        navigate(`/teacherratings/${subjectId}/${date}`);
      }
      
      return(
        <>
         
    <div>
  <div className="row" style={{ fontWeight: "bold", marginTop:'80px'}}>
    <div className="col"></div>
    <div
      className="col-lg-6"
      style={{
        backgroundColor: "Highlight",
        borderRadius: "20px",
        padding: "30px",
      }}
    >
      <div
        className="mb-3"
        style={{
          backgroundColor: "blue",
          borderRadius: "10px",
        }}
      >
        <h2
          style={{
            textAlign: "center",
            margin: "10px 0",
            color: "whitesmoke",
            fontSize: "24px",
          }}
        >
          <b>Teacher Ratings</b>
        </h2>
      </div>
      <div className="row mb-6 align-items-center">
  <div className="col-sm-3">
    <label className="mb-0" style={{ color: "#343a40", fontSize: "18px" }}>
      Subject Name
    </label>
  </div>
  <div className="col-sm-9">
    <select
      className="form-select form-select-lg"
      value={selectedSubject}
      onChange={handleSubjectChange}
      style={{
        backgroundColor: "#fff",
        borderRadius: "8px",
        padding: "10px",
        border: "2px solid #ced4da",
        boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
        fontSize: "18px",
        width: "100%",
      }}
    >
      <option value="">Select subject name</option>
      {subjects.map((subject) => (
        <option key={subject.subjectName} value={subject.subjectId}>
          {subject.subjectName}
        </option>
      ))}
    </select>
  </div>
</div>


<div className="row mb-5 align-items-center">
  <div className="col-sm-3">
    <label className="mb-0" style={{ color: "#343a40", fontSize: "18px" }}>
      Date
    </label>
  </div>
  <div className="col-sm-9">
                <input className="form-control"
                 style={{
                  backgroundColor: "#fff",
                  borderRadius: "8px",
                  padding: "10px",
                  border: "2px solid #ced4da",
                  boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
                  fontSize: "18px",
                  width: "100%",
                }}

                  type="date"
                  
                  onChange={(e) => {
                    setDate(e.target.value);
                  }}
                />
              </div>
              </div>
  
              <div className="row mb-5 align-items-center"></div>

      <div className="mb-3" style={{ textAlign: "center" }}>
        <button
          onClick={checkRatings}
          className="btn btn-success"
          style={{
            backgroundColor: "green",
            color: "white",
            borderRadius: "8px",
            padding: "10px 20px",
            fontSize: "16px",
          }}
        >
         Check Ratings
        </button>
      </div>
    </div>
    <div className="col"></div>
  </div>
</div>
        </>
      )
}
export default CheckTeacherRatings;