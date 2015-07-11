import random
import itertools
import numpy as np
if __name__ == '__main__':
  num_points = 1000
  largest_count = 0
  largest_set = []
  tmp = []
#   create random points
  [tmp.append([random.randint(0, 1000), random.randint(0, 1000)]) for t in range(num_points)]
  matrix = [[0, 0, 1], [0, 0, 1], [0, 0, 1]]
#   get array of all 2-tuple of index with no repeatations - ensured by
#   making sure x < y in the tuple (x, y)
  tmp_iter = [itertools.product([t], range(t+1, 100)) for t in range(100)]
  flattened = [val for sublist in tmp_iter for val in sublist]
  flattened = list(set(flattened))
  for idx_tuple in flattened:
    if largest_count == 0:
      largest_set = [tmp[idx_tuple[0]], tmp[idx_tuple[1]]]
      largest_count = 2
      print largest_set, largest_count
    matrix[0][0] = tmp[idx_tuple[0]][0]
    matrix[0][1] = tmp[idx_tuple[0]][1]
    matrix[1][0] = tmp[idx_tuple[1]][0]
    matrix[1][1] = tmp[idx_tuple[1]][1]
    latest_count = 2
    latest_set = [tmp[idx_tuple[0]], tmp[idx_tuple[1]]]
    for i in range(idx_tuple[1] + 1, num_points):
      matrix[2][0] = tmp[i][0]
      matrix[2][1] = tmp[i][1]
      if np.linalg.det(np.array(matrix, np.float32)) == 0.0:
        latest_count = latest_count + 1
        latest_set.append(tmp[i])
        if latest_count > largest_count:
          largest_count = latest_count
          largest_set = latest_set
          print largest_set, largest_count
