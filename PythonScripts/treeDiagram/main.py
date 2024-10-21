import random
import matplotlib.pyplot as plt
import networkx as nx

class TreeNode:
    def __init__(self, value, children=None):
        self.value = value
        self.children = children if children is not None else []

def generate_random_tree(depth, max_children=3):
    if depth == 0:
        return TreeNode(random.randint(1, 100))

    num_children = random.randint(1, max_children)
    children = [generate_random_tree(depth - 1, max_children) for _ in range(num_children)]
    return TreeNode(0, children)

def draw_tree(root):
    G = nx.DiGraph()
    labels = {}

    def add_edges(node, parent=None):
        if parent is not None:
            G.add_edge(parent, node)
        labels[node] = node.value
        for child in node.children:
            add_edges(child, node)

    add_edges(root)
    pos = nx.multipartite_layout(G, subset_key=lambda n: len(nx.shortest_path(G, root, n)) - 1)
    nx.draw(G, pos, with_labels=True, labels=labels, node_size=700, node_color='skyblue', font_size=10, font_weight='bold', arrows=True)
    plt.show()

# Generate a random tree with depth 4
root = generate_random_tree(4)

# Draw the tree
draw_tree(root)