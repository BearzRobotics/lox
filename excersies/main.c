#include <stdlib.h>
#include <stdio.h>

// Double link list node
struct Node {
	// data we store in each node
	int data;

	// pointer to previous node
	struct Node* prev;

	// pointer to next node
	struct Node* next;

};

// Function to create new Nodes
struct Node *createNode(int new_data) {
	struct Node *new_node = (struct Node *)
	malloc(sizeof(struct Node));
	new_node->data = new_data;
	new_node->next = NULL;
	new_node->prev = NULL;
	return new_node;
}

// We want to create a function to 

int main() {
	// lets do something with are double link list node
	for (int i=0; i < 20; i++) {
		struct Node* node = createNode(i);

		// just calling node prints all values
		// node->data only prints the data value
		printf("Data: %d\n", node->data);

		printf("Previous Node: %d\n", node->prev);

	}

	return 0;
}
