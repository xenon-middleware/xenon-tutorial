#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""Tests for the pyxenon_snippets module.
"""
import pytest

from pyxenon_snippets import pyxenon_snippets


def test_something():
    assert True


def test_with_error():
    with pytest.raises(ValueError):
        # Do something that raises a ValueError
        raise(ValueError)


# Fixture example
@pytest.fixture
def an_object():
    return {}


def test_pyxenon_snippets(an_object):
    assert an_object == {}
