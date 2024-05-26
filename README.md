# SET-UP
-   Open a git bash/IDE terminal in your local machine where you want to clone the repository
-   Clone repository (run the ff command) 
    - git clone https://github.com/AldrichJohne/notetaking.git
-   Run the project

# HOW TO USE THE API's
-   NOTE: use the port where your application ran
-   Create Note
    -   method: POST
    -   url: http://localhost:8080/notes
    -   body: 
        {
            "title": "Title",
            "body": "This is notes body"
        }
-   Retrieve all Notes
    -   method: GET
    -   url: http://localhost:8080/notes
-   Retrieve a Note
    -   method: GET
    -   url: http://localhost:8080/notes/id-placeholder
    -   sample: http://localhost:8080/notes/0
-   Update a Note
    -   method: PUT
    -   url: http://localhost:8080/notes/id-placeholder
    -   sample: http://localhost:8080/notes/0    
    -   body:
        {
        "title": "Updated Title",
        "body": "Updated Body"
        }
-   Remove a Note
    -   method: DELETE
    -   url: http://localhost:8080/notes/id-placeholder
    -   sample: http://localhost:8080/notes/0
    
