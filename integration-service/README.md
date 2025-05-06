# Order Handing System

The Order Handling System (OHS) is a grocery order management system designed to handle client orders effectively.

## Getting Started
Follow these steps to get the project up and running on your local machine:

- Step 1: Clone the repository
- Step 2: Installation and Running the application



### Step 1: Clone the repository
```shell
git clone https://github.com/monajahromi/ohs-api-test.git
 cd  ohs-api-test\integration-service
```

### Step 2: Installation and Running the application
You can run the application using Docker or by running the Spring Boot main class.
Both methods will expose port 50050 on your local machine.

#### Using Docker
Build the Docker image by running the following command in the terminal:
```shell
docker build -t integration-service:amd64 .
```

go to the docker-compose directory
```shell
cd ohs-api-test\intel
docker-compose up
```


### Starting the job
The order integration job needs to be triggered manually by calling the following endpoint:

-- localhost:50050/its/job/order

