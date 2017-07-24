# xenon-rse2017-tutorial

Material for [Xenon](http://nlesc.github.io/Xenon/) tutorial for [Research Software Engineers Conference 2017](http://rse.ac.uk/conf2017/).

## Installation

Installation of tutorial material in tutorial virtual machine.

```bash
cd xenon
git clone https://github.com/NLeSC/xenon-rse2017-tutorial.git .
install.sh
```

After which 
* the [Xenon cli](https://github.com/NLeSC/xenon-cli) has been installed as `~/xenon/xenon-cli/bin/xenon`.
* Slurm batch scheduler is runnin as Docker container, login with `ssh -p 2222 xenon@localhost` and password `javagat`.
