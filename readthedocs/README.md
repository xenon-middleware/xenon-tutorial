```bash
# install pip3
sudo apt install python3
sudo apt install python3-pip

# install the requirements for building the Sphinx tutorial
pip3 install --user -r requirements.txt

# build the HTML documentation from sources
sphinx-build -b html . build/

# use a browser to look at the result, e.g.
firefox build/index.html
```
