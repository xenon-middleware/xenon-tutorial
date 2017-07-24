# xenon-rse2017-tutorial

Material for [Xenon](http://nlesc.github.io/Xenon/) tutorial for [Research Software Engineers Conference 2017](http://rse.ac.uk/conf2017/).

## Installation

Step to install tutorial material in to tutorial virtual machine.

```bash
# Login as tutorial user
cd xenon
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git .
./install.sh
```

After which 
* the [Xenon cli](https://github.com/NLeSC/xenon-cli) has been installed as `~/xenon/xenon-cli/bin/xenon` and added to the $PATH env var.
* Slurm batch scheduler is running as Docker container, to login use `ssh -p 2222 xenon@localhost` and password `javagat`.

## Test

xenon slurm --location localhost:2222 exec
