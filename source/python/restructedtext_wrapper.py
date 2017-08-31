import sys


class Wrapper:
    def __init__(self, filename):
        self.filename_in = filename
        self.filename_out = self.filename_in + '.txt'
        self.content = None

    def wrap(self):

        self.content = '.. code-tab:: python\n'
        self.content += '\n'

        with open(self.filename_in) as f:
            lines = f.readlines()

        for line in lines:
            self.content += ' ' * 4 + line

    def print(self):
        print(self.content)

    def write(self):
        with open(self.filename_out, "w") as f:
            print("{}".format(self.content), file=f)

if __name__ == '__main__':

    def wrapit():
        for filename in sys.argv[1:]:
            wrapper = Wrapper(filename)
            wrapper.wrap()
            wrapper.write()

    wrapit()
