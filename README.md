# Important
This project is not currently maintained and requires an adoption of the cli of ludwig to some POJO classes. There is a automated generator included. The whole project is untested!

# About
Gustav is a JVM (Groovy) based REST-Wrapper for uber/ludwig

Using Uber's Ludwig on a single computer is a great experience. You can use it to experiment with your data or even serve your data using the internal web server.
Running experiments using multiple servers, distributed over a network is currently not possible. There is no web interface at all.
Gustav tries to fix this giving a REST interface to basic IO operations, process access and log access.


# File
Because of the way Ludwig is designed, it's heavily based on file and directory operations.
Therefor, Gustav is providing a simple and effective way to access the servers file system. Uploading, moving and removing files and folders works out of the box.

## index (get)
You can create a full overview of a directory using a simple get request:
```
/<workspace/index
```
| parameter | description                                       |
|-----------|---------------------------------------------------|
| p         | Path of the directory relative to the workspace   |
| m         | Method to index, use r to get all files recursive |

## delete (get)
```
/<workspace/delete
```
Used to delete files and directories
| parameter | description                                       |
|-----------|---------------------------------------------------|
| p         | Path of the directory relative to the workspace   |

## get (get)
```
/<workspace/get
```
Used to get a file or directory
When the given path is a directory, a zip file will be crated and served as download
| parameter | description                                       |
|-----------|---------------------------------------------------|
| p         | Path of the directory relative to the workspace   |

## add (post)
```
/<workspace/add
```
Used to add and overwrite a file or directory
When posting a zip file, the file will be extracted to the given path, use the m
parameter with k (as "keep") to disable the extraction
| parameter | description                                       |
|-----------|---------------------------------------------------|
| p         | Path of the directory relative to the workspace   |
| m         | k (keep) or u (unpack) used for zip files   |


# Project
Gustav is project based, each project got it's own workspace and is separated from other projects

# create
Create a new project
```
/project/create
```
| parameter | description                                       |
|-----------|---------------------------------------------------|
| name      | Name of the project |

# index
Show a list of all projects on this server
```
/project/index
```

# delete
Used to delete a project including all files and directories
```
/project/delete
```
| parameter | description                                       |
|-----------|---------------------------------------------------|
| id        | Id of a project |


# Proxy
Given the new predict serve function, Gustav is also able to give access to this new interface.
A reverse proxy is available using 

# start
Used to start a new serve session, a session identifier is returned
```
/project/<id>/proxy
```
| parameter  | description                                           |
|------------|-------------------------------------------------------|
| model_path | Path of the model to serve, relative to the workspace |


# route
Used to send data to the server endpoint and get the result
| parameter  | description                                           |
|------------|-------------------------------------------------------|
| s | Session to use |

# stop
Used to stop a serve process
| parameter  | description                                           |
|------------|-------------------------------------------------------|
| s | Session to use |


# Command

[work in progress]
