```bash
# get a copy of the repository
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git
cd xenon-rse2017-tutorial

# install virtualenv from (ubuntu) repositories
sudo apt install virtualenv

# make a Python 2 virtual environment 
virtualenv -p /usr/bin/python2.7 .venv27

# activate the virtual environment
source ./.venv27/bin/activate

# upgrade pip if necessary
pip install --upgrade pip

# install the requirements for building the Sphinx tutorial
pip install -r requirements.txt

# build the HTML documentation from sources
sphinx-build -b html ./source ./docs

# use a browser to look at the result, e.g.
firefox build/index.html
```
