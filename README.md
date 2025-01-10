Repository created for the paper "LLM Prompt Engineering for Automated White-Box Integration Test Generation in REST APIs", submitted to the 5th International Workshop on Artificial Intelligence in Software Testing.

Repository Structure
1. PythonScript.py
  Description: Main script that implements the generation and refinement of test suites using the OpenAI API.
  Objective: Automate the creation and optimization of tests based on different prompt versions.

2. EMB Projects
  Description: This is a a folder containing the EvoMaster Benchmark (EMB) projects (github.com/WebFuzzing/EMB).
  Modifications:
    The configurations have been adapted to enable integration with PythonScript.py.
  
3. Full Experiment Data
  Description: Contains detailed tables with data from each test suite generation.
  Organization:
    The results are organized by prompt version.
    Metrics include code coverage, token consumption, and execution time.

4. Generated Tests
  Description: Repository of all files generated during the execution of EvoMaster and PythonScript.py.
  Subfolders sample:
    EvoMaster:
      Location: market-test-set/evomaster/
      Description: Contains test and coverage files for each of the 10 EvoMaster executions using seeds 01 to 10.
    OpenAI GPT (PythonScript):
      Location: market-test-set/gpt/v0/gpt35/
      Description: Contains test and coverage files generated by PythonScript.py using the GPT-3.5 Turbo model and prompt version v0.
      Executions: Results from 3 runs (run01, run02, and run03) for each configuration.
