```bash

# install virtualenv from (ubuntu) repositories
sudo apt install virtualenv

# make a Python 3 virtual environment 
virtualenv -p /usr/bin/python3.7 .venv37

# activate the virtual environment
source ./.venv37/bin/activate

# upgrade pip if necessary
pip install --upgrade pip

# install the requirements for building the Sphinx tutorial
pip install -r requirements.txt

# build the HTML documentation from sources
sphinx-build -b html . ../docs

# use a browser to look at the result, e.g.
firefox docs/index.html
```
