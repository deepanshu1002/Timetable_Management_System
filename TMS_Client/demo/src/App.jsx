import { useDispatch, useSelector } from 'react-redux'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import { useEffect } from 'react'
//used to register react-toastify
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'

import RegisterUser from './components/registerUser'
import ManageUsers from './components/ManageUser'
import ClassRoom from './components/classRoom'
import LoginUser from './components/loginUser';
import Student from './components/student';
import Teacher from './components/teacher';
import Admin from './components/admin';
import LeaveApplicationList from './components/getLeaveApplication '

import GetLeaveApplication from './components/getLeaveApplications'
import LeaveApplication from './components/leaveApplication';


import AddSubjectDetails from './components/subjectDetails';
import AddLabVenue from './components/labDetails';
import AddClassroom from './components/classRoomDetails';
import AddDepartment from './components/departmentDetails';
import ViewTimetable from './components/timeTable'
import AddLectureData from './components/addLectureData'


function App() {
return (
    <div className='container-fluid'>
    
      <div className='container'>
        <Routes>
        <Route path='/' element={<LoginUser/>} />
         {/* register component */}
         <Route path='/register' element={<RegisterUser />} />
         <Route path='/validuser' element={<ManageUsers />} />  
         {/*Leave Application component */}
         <Route path='/LeaveApplication' element={<LeaveApplication />} />
         {/*GET Leave Application component */}
         <Route path='/GetLeaveApplication' element={<GetLeaveApplication/>} />
         {/* Class Room Component */}
         <Route path='/ClassRoom' element={<ClassRoom />} />

         <Route path='/student' element={<Student/>} />

         <Route path='/teacher' element={<Teacher/>} />

         <Route path='/admin' element={<Admin/>} />
         
          <Route path='/managedepartment' element={<AddDepartment/>} />
          
         <Route path='/manageclassroom' element={<AddClassroom/>} />

         <Route path='/managelab' element={<AddLabVenue/>} />

         <Route path='/managesubject' element={<AddSubjectDetails/>} />

         <Route path='/timetable' element={<ViewTimetable/>} />

         <Route path='/addlecturedata' element={<AddLectureData/>} />
        </Routes>
      </div>
      <ToastContainer />
    </div>
  )
}
  export default App
