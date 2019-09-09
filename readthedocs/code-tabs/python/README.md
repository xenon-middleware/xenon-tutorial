
```
# install virtualenv from ubuntu repositories
sudo apt install virtualenv

# create a new virtual environment
virtualenv -p /usr/bin/python3.6 venv36

# activate the new environment
source venv36/bin/activate

# install the requirements and test requirements
pip install -r requirements.txt
pip install -r requirements-dev.txt

# check if xenon-grpc can be found
which xenon-grpc

# run the tests (some may fail due to missing fixtures)
pytest

```
