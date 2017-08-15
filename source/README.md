
add verbosity in case of problems

point out simplicity of changing from file to sftp to webdav

todo add wait for cat job if cat job took 50 seconds
upload-submit-download -> combine in one statement
but what if job takes long (e.g. when resources are in use or network is slow)
need wait command: xenon scheduler slurm wait.
but this wait needs to be told what to wait for,
so first we need xenon scheduler slurm list / capture jobid



# leftovers

Make a directory

.. code-block:: bash

      xenon filesystem file mkdir xenoncli-made-this-dir
      xenon filesystem file mkdir --parents xenoncli-made-this-dir/thesubdir/thesubsubdir

Copy a directory recursively

.. code-block:: bash

      xenon filesystem file copy --recursive xenoncli-made-this-dir xenoncli-copied-this-dir

Rename/move a file

.. code-block:: bash

      xenon filesystem file rename xenoncli-copied-this-dir/ xenoncli-moved-this-dir

Remove a directory

.. code-block:: bash

      xenon filesystem file remove xenoncli-made-this-dir/thesubdir/thesubsubdir

Remove a directory (recursively)

.. code-block:: bash

      xenon filesystem file remove --recursive xenoncli-moved-this-dir/

