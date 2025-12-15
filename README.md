# 🎓 Labeeb – Reward-Based One-on-One Learning Platform

## Overview
**Labeeb** is a backend system for a reward-based educational platform designed specifically for **elementary school students**.

The platform focuses on **one-on-one learning sessions** between students and private teachers, with active involvement from parents. It uses **gamification and rewards** to motivate young learners, while also providing parents with full visibility and control, and teachers with a flexible income opportunity.

> **Core Idea:** Learning through motivation, rewards, and personalized attention.

---

## Target Audience
- 👦 **Elementary Students** – Learn through rewards, games, and personalized tasks
- 👨‍👩‍👧 **Parents** – Monitor progress, manage payments, and motivate children
- 👨‍🏫 **Private Teachers** – Earn income through structured one-on-one teaching

---

## Key Concepts
- **One-on-One Learning:** Each session includes one student and one teacher only
- **Reward-Based System:** Students earn points and balance for completed tasks
- **Gamification:** Leaderboards, quizzes, games, and gift cards
- **Parental Control:** Parents manage wallets, payments, and reports
- **Teacher Monetization:** Teachers earn based on teaching and task correction

---

## System Roles (Logical – No Authentication)
> ⚠️ The system does **NOT** implement authentication or authorization. All role separation is handled logically using IDs.

### Admin
- Approves or rejects teachers
- Manages categories
- Processes refund requests
- Views system-wide data (students, parents, payments, sessions)

### Parent
- Registers children (students)
- Adds balance to wallet
- Generates discount codes (based on number of children)
- Purchases courses (full or installments)
- Requests refunds
- Receives weekly reports via email
- Creates tasks for students
- Reviews teachers

### Student
- Attends sessions
- Submits tasks
- Earns points and balance
- Appears on leaderboards
- Plays quizzes and educational games
- Uses balance to buy gift cards
- Receives assessments (e.g. English speaking)

### Teacher
- Registers and awaits admin approval
- Belongs to one category
- Creates courses and schedules
- Creates and corrects tasks
- Assigns grades and reward points
- Receives ratings and reviews
- Earns balance as income

---

## Core Features

### 📚 Courses & Scheduling
- Teachers create courses with prices and descriptions
- Each course has multiple schedules (dates, times, days)
- Students register per **course schedule**, not per course
- Availability tracking (Available / Registered)

### 💳 Payment System
- Full payment or installment plans
- Payments are linked to **CourseSchedule**
- Discount codes supported
- Refund requests require admin approval
- Receipts are generated and sent via email

### 🏅 Reward & Gamification System
- Tasks have grades and points
- Points are converted into student balance
- Rewards motivate children to complete tasks
- Leaderboards rank students by total grades
- Students can buy gift cards using their balance

### 📝 Tasks & Feedback
- Tasks can be created by teachers or parents
- Task lifecycle:
  - Pending → Uploaded → Approved / Rejected
- Teachers correct tasks and assign grades
- Feedback is stored separately per task

### 🧑‍🏫 Sessions & Attendance
- Sessions belong to course schedules
- Attendance tracking (Attended / Absent / Late)
- Students can upload absence excuses
- Automatic session URL generation (e.g., Zoom)

### 📊 Reports & Assessments
- Weekly student performance reports sent to parents
- English speaking assessment using external automation (n8n)
- Stores strengths, weaknesses, and overall evaluation

### 🎮 Games & Quizzes
- Interactive quizzes during free time
- AI-generated educational games
- Designed to increase engagement and motivation

---

## Database Design
The system uses a relational database with entities such as:
- Admin
- Parent
- Student
- Teacher
- Category
- Course
- CourseSchedule
- Session
- Task
- TaskFeedback
- StudentPayment
- StudentAssessment
- GiftCard
- TeacherReview

The **CourseSchedule** entity is a core component that connects:
- Course
- Student
- Payment
- Sessions

---

## API Design Notes
- RESTful API structure
- No authentication or authorization
- Role validation handled via IDs
- Clear separation of responsibilities per controller

---

## Tech Stack
- **Backend:** Spring Boot
- **ORM:** Hibernate / JPA
- **Database:** MySQL
- **Validation:** Jakarta Validation
- **Automation & AI:** n8n workflows
- **Build Tool:** Maven

---

## Limitations & Notes
- ❌ No authentication or security layer
- ❌ No role-based access control
- Designed for academic and educational demonstration

---

## API Endpoints Summary

| Controller | Endpoint Count |
|---|---:|
| AdminController | 4 |
| CategoryController | 4 |
| CourseController | 4 |
| CourseScheduleController | 6 |
| GiftCardController | 2 |
| N8NAssessmentController | 3 |
| ParentController | 7 |
| QuizController | 1 |
| SessionController | 7 |
| StudentController | 7 |
| StudentPaymentController | 7 |
| TaskController | 14 |
| TaskFeedbackController | 5 |
| TeacherController | 9 |
| TeacherReviewController | 4 |
| **Total** | **84** |

---

## Team Responsibilities & Roles

### 👤 Nawaf
**Primary Focus:** Core system logic, payments, and student management

**Responsibilities:**
- Relations, Models, And Validation Checkup and logic
- Admin, Parent, and Student core management
- Payment system implementation (Full payment, installments, refunds)
- Wallet & balance handling (Parent / Student)
- Task creation and lifecycle (Teacher & Parent tasks)
- Leaderboard logic
- Gift card system
- Overall system stability and data integrity

---

### 👤 Fouz
**Primary Focus:** Learning experience, sessions, gamification, and reporting

**Responsibilities:**
- Course and course scheduling logic
- Session management (attendance, excuses, session URLs)
- Task correction and reward calculation
- Weekly performance reports for parents
- Gamification features (quizzes, games)
- User engagement and motivation features

---

### 👤 Abdulrahman
**Primary Focus:** Teacher management, assessments, and AI integrations with (n8n)

**Responsibilities:**
- Teacher registration and approval workflow
- Category management
- Teacher rating and review system
- AI-powered English speaking assessment (n8n integration)
- Task feedback and AI-based grade prediction
- Ensuring teaching quality and assessment accuracy

---

## Detailed API Endpoints

### AdminController (`/api/v1/admin`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all admins | Nawaf |
| POST | `/add` | Add new admin | Nawaf |
| PUT | `/update/{id}` | Update admin | Nawaf |
| DELETE | `/delete/{id}` | Delete admin | Nawaf |

### CategoryController (`/api/v1/category`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all categories | Abdulrahman |
| POST | `/add/admin-id/{adminId}` | Add category | Abdulrahman |
| PUT | `/update/admin-id/{adminId}/category-id/{id}` | Update category | Abdulrahman |
| DELETE | `/delete/admin-id/{adminId}/category-id/{id}` | Delete category | Abdulrahman |

### CourseController (`/api/v1/course`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all courses | Fouz |
| POST | `/add/{teacherId}` | Add course | Fouz & Nawaf |
| PUT | `/update/{courseId}/{teacherId}` | Update course | Fouz & Nawaf |
| DELETE | `/delete/{id}/{teacherId}` | Delete course | Fouz & Nawaf |

### CourseScheduleController (`/api/v1/schedule`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all schedules | Fouz |
| GET | `/get/{id}` | Get schedule by ID | Fouz |
| POST | `/add/teacher-id/{teacherId}/course-id/{courseId}` | Add course schedule | Fouz & Nawaf |
| PUT | `/update/id/{id}/teacher-id/{teacherId}` | Update schedule | Fouz & Nawaf |
| DELETE | `/delete/id/{id}/teacher-id/{teacherId}` | Delete schedule | Fouz & Nawaf |
| GET | `/available-days/{courseId}` | Get available days | Nawaf |

### GiftCardController (`/api/v1/giftcard`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/student/{studentId}` | Get student gift cards | Nawaf |
| POST | `/buy/{studentId}` | Buy gift card | Nawaf |

### ParentController (`/api/v1/parent`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get/admin-id/{adminId}` | Get all parents | Nawaf |
| POST | `/add` | Add parent | Nawaf |
| PUT | `/update/{id}` | Update parent | Nawaf |
| DELETE | `/delete/{id}` | Delete parent | Nawaf |
| PUT | `/add-balance/{parentId}/{amount}` | Add parent balance | Nawaf |
| GET | `/discount/{parentId}` | Generate discount code | Fouz |
| GET | `/weekly-report/{parentId}/{studentId}/{courseScheduleId}` | Send weekly report | Fouz |

### StudentController (`/api/v1/student`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get/admin-id/{adminId}` | Get all students | Nawaf |
| POST | `/add/parent-id/{parentId}` | Add student | Nawaf |
| PUT | `/update/student-id/{id}/parent-id/{parentId}` | Update student | Nawaf |
| DELETE | `/delete/student-id/{id}/parent-id/{parentId}` | Delete student | Nawaf |
| GET | `/by-parent/{parentId}` | Get students by parent | Fouz |
| GET | `/leaderboard/{courseId}` | Get leaderboard | Nawaf |

### StudentPaymentController (`/api/v1/student-payment`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get/{adminId}` | Get all payments | Nawaf |
| POST | `/buy/full/{parentId}/{studentId}/{scheduleId}` | Buy course (full payment) | Nawaf |
| POST | `/buy/installments/{parentId}/{studentId}/{scheduleId}` | Buy course (installments) | Nawaf |
| PUT | `/pay-installment/{parentId}/{studentPaymentId}` | Pay installment | Nawaf |
| POST | `/refund/{parentId}/{paymentId}` | Request refund | Fouz |
| PUT | `/refund/{adminId}/{paymentId}` | Process refund | Fouz |
| GET | `/receipt/{paymentId}` | Get receipt | Fouz |

### SessionController (`/api/v1/session`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get-all/{adminId}` | Get all sessions | Fouz |
| POST | `/add/{courseScheduleId}` | Add session | Fouz |
| PUT | `/update/{sessionId}` | Update session | Fouz |
| DELETE | `/delete/{sessionId}` | Delete session | Fouz |
| GET | `/attendance/{studentId}/{courseScheduleId}` | Attendance report | Fouz |
| POST | `/excuse/{studentId}/{sessionId}` | Upload excuse | Fouz |
| POST | `/generate-url/{sessionId}` | Generate session URL | Abdulrahman |

### TaskController (`/api/v1/task`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get/{adminId}` | Get all tasks | Nawaf |
| POST | `/add/teacher/{studentId}/{teacherId}` | Add task by teacher | Nawaf |
| POST | `/add/parent/{studentId}/{teacherId}/{parentId}` | Add task by parent | Nawaf |
| PUT | `/update/{taskId}/{teacherId}` | Update task | Nawaf |
| DELETE | `/delete/teacher/{taskId}/{teacherId}` | Delete task by teacher | Nawaf |
| DELETE | `/delete/parent/{taskId}/{parentId}` | Delete task by parent | Nawaf |
| GET | `/expired/{teacherId}` | Get expired tasks | Fouz |
| PUT | `/correct/{taskId}/{teacherId}/{grade}` | Correct task & reward | Fouz |
| GET | `/parent-tasks/{parentId}` | Tasks by parent | Abdulrahman |
| GET | `/teacher-tasks/{teacherId}` | Tasks by teacher | Abdulrahman |
| GET | `/pending/{studentId}` | Pending tasks | Fouz |
| PUT | `/submit/{studentId}/{taskId}` | Submit task | Fouz |
| PUT | `/approve/{teacherId}/{taskId}` | Approve task | Fouz |
| PUT | `/reject/{teacherId}/{taskId}` | Reject task | Fouz |

### TaskFeedbackController (`/api/v1/feedback`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all feedbacks | Fouz |
| POST | `/add/{taskId}/{teacherId}` | Add feedback | Fouz |
| PUT | `/update/{feedbackId}/{teacherId}` | Update feedback | Fouz |
| DELETE | `/delete/{feedbackId}/{teacherId}` | Delete feedback | Fouz |
| POST | `/predict/{teacherId}/{taskId}` | Predict grade (AI) | Abdulrahman |

### TeacherController (`/api/v1/teacher`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all teachers | Abdulrahman |
| POST | `/add` | Add teacher | Nawaf |
| PUT | `/update/{id}` | Update teacher | Nawaf |
| DELETE | `/delete/{id}` | Delete teacher | Abdulrahman |
| GET | `/get-by-category/{categoryId}` | Teachers by category | Abdulrahman |
| GET | `/get-by-rating` | Teachers by rating | Abdulrahman |
| GET | `/get-pending-teachers/{adminID}` | Pending teachers | Abdulrahman |
| PUT | `/accept-teacher/{adminId}/{teacherId}` | Accept teacher | Abdulrahman |
| PUT | `/reject-teacher/{adminId}/{teacherId}` | Reject teacher | Abdulrahman |

### TeacherReviewController (`/api/v1/teacher-review`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get` | Get all teacher reviews | Abdulrahman |
| POST | `/add/{parentId}/{teacherId}` | Add teacher review | Abdulrahman |
| PUT | `/update/{teacherReviewID}` | Update teacher review | Abdulrahman |
| DELETE | `/delete/{teacherReviewID}` | Delete teacher review | Abdulrahman |

### N8NAssessmentController (`/api/v1/n8n`)
| Method | Path | Description | Author |
|---|---|---|---|
| GET | `/get-all/{adminId}` | Get all Assessments | Abdulrahman |
| POST | `/assessment-request` | Assessment Request (AI) | Abdulrahman |
| DELETE | `/delete-assessment/{studentId}/{assessmentId}` | Delete Assessment | Abdulrahman |

### QuizController (`/api/v1/quiz`)
| Method | Path | Description | Author |
|---|---|---|---|
| POST | `/play` | Play Quiz (AI) | Fouz |

---

## Models Summary
| Model | Author |
|---|---|
| Admin | Nawaf |
| Parent | Nawaf |
| Student | Nawaf |
| Teacher | Abdulrahman & Nawaf |
| Category | Abdulrahman |
| Course | Nawaf & Fouz |
| CourseSchedule | Nawaf & Fouz |
| Session | Fouz & Abdulrahman |
| Task | Nawaf |
| TaskFeedback | Fouz |
| StudentPayment | Nawaf |
| StudentAssessment | Abdulrahman |
| GiftCard | Nawaf |
| TeacherReview | Nawaf & Abdulrahman |

---

## Summary
**Labeeb** is a child-centered educational backend platform that combines:
- Personalized one-on-one learning
- Gamification and reward systems
- Parental involvement
- Teacher income generation

The system focuses on **motivation-driven education**, making learning engaging for children while giving parents transparency and teachers structured opportunities.

---

> 🎯 *Labeeb turns learning into a rewarding experience.*

