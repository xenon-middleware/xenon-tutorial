#!/usr/bin/env python
import pytest


from pyxenon_snippets import slurm_queues_getter


def test_slurm_queues_getter():
    slurm_queues_getter.run_example()

