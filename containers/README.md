
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


# Custom config, server-side

```
diff --suppress-common-lines --side-by-side ssh-server/sshd-default-config ssh-server/sshd-system-config
#Port 22                              | Port 10022
#HostKey /etc/ssh/ssh_host_ecdsa_key  | HostKey /etc/ssh/ssh_host_ecdsa_key
#PubkeyAuthentication yes             | PubkeyAuthentication no
#PasswordAuthentication yes           | PasswordAuthentication yes
```

# Custom system config, client-side

```
diff --suppress-common-lines --side-by-side ssh-client/ssh-default-config ssh-client/ssh-system-config
(empty)
```