# Education Control System
## List of changes
| Date | Version | Description | Author |
| ------------ | ------------- | ------------ | ------------- |
| 21.09.2020 | 1.0 | Educational Control System | TeamOne |

## Contents
1. [Introduction](#1-introduction)  
1.1 [Target](#11-target)  
1.2 [Context](#12-context)  
1.3 [List of abbreviations and definitions](#13-list-of-abbreviations-and-definitions)  
2. [Short product overview](#2-short-product-overview)  
3. [Characteristics of the business processes](#3-characteristics-of-the-business-processes)  
3.1 [System purpose](#31-system-purpose)  
3.2 [Practicality](#32-practicality)  
3.3 [Interaction with users](#33-interaction-with-users)  
3.4 [The business process characteristics](#34-the-business-process-characteristics)  
4. [Functionality](#4-functionality)  
4.1 [Unauthorized user feature description](#41-unauthorized-user-feature-description)  
4.2 [Student user feature description](#42-student-user-feature-description)  
4.3 [Leader of the group user feature description](#43-leader-of-the-group-user-feature-description)  
4.4 [Teacher user feature description](#44-teacher-user-feature-description)  
4.5 [Administrator user feature description](#45-administrator-user-feature-description)  
5. [Availability](#5-availability)  
5.1 [Localization](#51-localization)  
5.2 [Platforms](#52-platforms)  
6. [Reliability](#6-reliability)  
7. [Security](#7-security)  
## 1. Introduction
The document contains descriptive information about the requests of people interested in this service. Stakeholders are primarily users and administrators; they can be students, teachers and other people who want to use the service.
#### 1.1 Target
This document aims to define the basic requirements for development, functional space, performance and serviceability. In addition, the document covers the analysis of stakeholder requests, namely technical limitations, limits and deadlines.
#### 1.2 Context
The list of requirements given in the document is the basis of the technical task for the development of this service.
#### 1.3 List of abbreviations and definitions
Application Programming Interface (API) is a set of subroutine definitions, interoperability protocols, and software development tools. Simplified is a set of well-defined methods for the interaction of different components. The API provides the developer with tools for rapid software development. API can be for web-based systems, operating systems, databases, hardware, software libraries.

A Java Virtual Machine (JVM) is a set of computer programs and data structures that use a virtual machine model to execute other computer programs or scripts.
## 2. Short product overview
The Education Control System is a system that provides the necessary information and a comfortable connection between students and teachers. The database of the system can store materials of lectures, practical tasks, events, schedules for all probable groups, both students and separately for teachers.
## 3. Characteristics of the business processes
#### 3.1 System purpose
The main idea of our project is to create a service that provides students with a schedule that is combined with the learning process, and the opportunity for students to influence the above things. We want to create a very simple and flexible application programming interface (API). For example, in the form of a telegram bot, so that any university or student community can afford its rapid implementation and efficient operation. Of course, first of all, our project is designed for student communities, which in the conditions of inefficient work of the university administration could timely provide the necessary information to students.
#### 3.2 Practicality
The concept of the scheduling system, for both students and teachers, provides a large number of users of the system, that is people who want to use it.
However, there are many options for integrating this system into other software products, from telegrams to the official website of the university.
#### 3.3 Interaction with users
In general, user interaction depends on the application of the API. Yes, if necessary, it will be done via email.
#### 3.4 The business process characteristics
The system will be managed with the help of administrator accounting tools (their exclusive functions), that is with the help of a special administration team and system operators.
## 4. Functionality
There are several modes of using the service. This creates a hierarchical approach to the interaction between different types of users and elements of the service system. Each of the user models has different functions and separate concepts of interaction with the service, which improves the work for all stakeholders.
#### 4.1 Unauthorized user feature description
This type of user is designed to quickly view the schedule if you do not need to use other system functions.

Available features:
* view the schedule of groups;
* view the schedule of teachers;
* log in to an existing account.

#### 4.2 Student user feature description
The student's account capabilities allow you to view all the functionality of the program without being able to make any changes.

Available features:
* view group schedules;
* view the schedule of teachers;
* view homework;
* view own attendance.

#### 4.3 Leader of the group user feature description
The leader of the group user is a representative of the students of the group, who is elected by the majority of students of the academic group by direct vote. The capabilities of the elder's account provide a relationship between the students of the group and the teacher.

Available features:
* attach practical tasks and lecture materials to classes in the schedule;
* add to the schedule of training and consultation;
* provide a list of students present at a particular class;
* use student user type capabilities.

#### 4.4 Teacher user feature description
The separation of this type of user in the system facilitates the pedagogical activities of teachers. The capabilities of the teacher's account allow you to automate certain aspects of his professional activity.

Available features:
* view the schedule for the actual teacher and groups;
* monitor students' attendance at their classes;
* distribute homework among group students;
* create certain off-schedule meetings for student consultations, etc.

#### 4.5 Administrator user feature description
The administrator account has full access to the entire database, so it can affect
absolutely all the actions of other accounts. Can behave (be in a role) like all other existing accounts.

Available features:
* see schedules of both students and teachers;
* add and change schedules of groups and teachers;
* manage records of students, teachers, elders, groups, namely: add new accounts, delete them and change their information;
* manage events and homework, student attendance records.

## 5. Availability
#### 5.1 Localization
Application software (API) must be localized in Ukrainian and English.

#### 5.2. Platforms
The application software (API) must run on all platforms supported by the JVM.

## 6. Reliability
Developers must ensure a high level of service resiliency. This will be provided by copying data, duplicating databases, etc.

## 7. Security
System user data must be securely protected from outsiders by arranging for authenticated access.
