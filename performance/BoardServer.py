from locust import HttpUser, task, between
import random

class BoardServer(HttpUser):
    
    def on_start(self):
        self.client.post("/users/sign-in", json={"userId": "test_user1",
                                                 "password": "123"})
    
    @task
    def view_search(self):
        sortStatus = random.choice(["CATEGORIES", "NEWEST", "OLDEST"])
        categoryId = random.randint(1, 10)
        name = "부하 테스트 게시글".join(random.randint(1, 100000))
        headers = {"Content-Type": "application/json"}
        data = {"sortStatus": sortStatus,
                "categoryId": categoryId,
                "name": name}
        
        self.client.post("search", json=data, headers=headers)