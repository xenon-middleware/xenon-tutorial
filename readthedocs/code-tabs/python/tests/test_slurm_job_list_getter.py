#!/usr/bin/env python
import pytest


from pyxenon_snippets import slurm_job_list_getter


def test_slurm_job_list_getter():
    slurm_job_list_getter.run_example()

