🌐 Language: [Español](FEATURES.md) | **English**

# Functionalities

## Overview

TurismoUY allows managing a tourism ecosystem composed of users, tourist activities, outings and registrations.

The system supports both:

- End users interacting through the web interface.
- Administrative users operating through the desktop (Swing) application.
- A shared backend that centralizes business rules and persistence.

This document describes what can be done from a functional perspective, without repeating architectural or setup details covered in other sections.

---

## User Management

### User Types

The system supports two main types of users:

- **Tourists**
- **Suppliers**

Each user has:

- Unique nickname
- Email
- Personal information (name, birth date, etc.)
- Profile image

### Available Operations

- User registration (tourist or supplier)
- Authentication (login/logout)
- Profile modification
- Profile image update
- User listing (administrative use)
- User data consultation

Passwords are stored securely using hashing mechanisms.

---

## Tourist Activities

Tourist activities are created by suppliers and represent the base offering of the platform.

Each activity includes:

- Name (unique)
- Description
- Duration
- Cost
- City
- Supplier

### Available Operations

- Activity creation (supplier)
- Activity modification
- Activity consultation
- Listing activities
- Filtering by supplier
- Filtering by status

Activities are subject to business validation rules before being made available in the system.

---

## Outings

An outing represents a scheduled instance of a tourist activity.

Each outing includes:

- Associated activity
- Date and time
- Maximum capacity
- Image (optional)

### Available Operations

- Outing creation
- Outing consultation
- Listing outings by activity

Outings allow the system to separate the conceptual activity from its concrete scheduled executions.

---

## Tourist Registrations

Tourists do not register directly to activities, but to specific outings.

Each registration:

- Links a tourist to an outing
- Is validated according to capacity and business rules

### Available Operations

- Registration to an outing
- Listing registrations of an outing
- Listing registrations of a tourist

The system prevents duplicate registrations and enforces capacity constraints.

---

## Administrative Flows

One of the most relevant aspects of the system is the existence of cross-interface workflows.

For example:

- A supplier may initiate the creation of an activity.
- An administrator, using the desktop application, may review or manage related data.
- The same centralized backend ensures consistent validation and persistence.

This demonstrates:

- Shared business logic.
- Centralized rule enforcement.
- Reuse of the same domain model across multiple interfaces.

---

## Persistence and Data Integrity

The system ensures:

- Relational consistency through JPA.
- Validation of business constraints.
- Prevention of duplicated entities (e.g., user nickname, activity name).
- Controlled management of images and associated resources.

---

## Scope and Limitations

The project intentionally focuses on:

- Correct layering.
- Distributed communication via SOAP.
- Persistence using JPA.
- Clear separation between domain and presentation.

It does not aim to include:

- Advanced authorization models.
- REST APIs.
- CI/CD pipelines.
- Production-grade infrastructure.

Its purpose is educational and architectural demonstration.
