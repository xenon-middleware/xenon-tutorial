
1. build the server

    ```
    docker build --tag ssh-server ssh-server/
    ```

1. build the client

    ```
    docker build --tag ssh-client ssh-client/
    ```

1. start the network

    ```
    docker network create ssh-testing-network
    ```

1. start the server

    ```
    docker create --network ssh-testing-network --name ssh-server --hostname ssh-server ssh-server
    docker start ssh-server
    
    ```
1. start the client

    ```
    docker run -it --network ssh-testing-network --name ssh-client --hostname ssh-client ssh-client /bin/sh
    ```


