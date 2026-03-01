# CP5307
Advanced Mobile Technology

DeepFocus

DeepFocus is a utility application designed to help users stay focused while working or studying. The app allows users to set a focus timer and temporarily lock their phone to reduce distractions and block notifications during the session. In addition, users can view posts shared by other users in the community section, creating a simple social environment that encourages focus and productivity.

## Version Development

### Version 1.0

The first version of DeepFocus focused on the core functionality of the application. It provided a simple focus timer that allowed users to set a duration and track their focus session. The main goal of this version was to implement the basic timing feature and ensure the application worked reliably.
Note: `DeepFocusHomeScreen.kt` belongs to Version 1.0 and was used with the original `MainActivity.kt` to implement the basic focus timer interface.

### Version 2.0

The second version introduced structural and functional improvements. A bottom navigation bar was added to support multiple screens within the application. 

The app was divided into three main screens:

- A home screen that displays focus information and user posts  
- A timer screen for managing focus sessions  
- A community screen where users can view updates shared by other users  

This version improved usability and expanded the app beyond a single-function timer into a more structured utility application.
### Version 3.0

In Version 3.0, the application moved from a structural layout prototype to a fully interactive utility app.

A complete timer mechanism was implemented using Compose state management and coroutine-based countdown logic. Users are now able to start, pause, and resume focus sessions, with real-time updates reflected in the UI.

A progress indicator was added to enhance visual feedback, and session tracking was introduced to monitor daily focus duration.

These improvements strengthened the application's core purpose as a focus-oriented utility tool and demonstrated deeper integration of Jetpack Compose state management.

### Version 4.0

Version 4.0 focused on improving application architecture and code structure while enhancing overall stability and maintainability.

In this version, state management was refactored using the ViewModel pattern to better separate UI logic from business logic. This change improved code organisation and aligned the application with modern Android architectural practices.

The focus timer system was further refined to ensure smoother state transitions between start, pause, and resume actions. Daily focus statistics are now managed more cleanly, supporting better scalability for future updates.

Additionally, the overall project structure was improved to reflect a more modular and maintainable design, preparing the application for future enhancements such as networking features and data persistence.

Version 4.0 represents the transition from a functional prototype to a more structured and production-oriented Android utility application.
