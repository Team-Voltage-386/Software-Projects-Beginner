---
typora-copy-images-to: ./assets
---

Let's solve our first programming challenge!

## Step1: Sign up at leetcode

Use your standard register and verify email steps to get an account on leetcode:

![image-20231204150630865](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204150630865.png)

## Step2: Find the first problem

Leetcode has hundreds of problems. The very first problem is called "Two Sum". You can find it in the problems list or navigate to it directly:

![image-20231204150817314](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204150817314.png)

## Step3: Understand how leetcode works

Leetcode is a website which presents you with a programming problem, and accepts your code as a solution. When you submit your code, leetcode will test your code with many problem instances and check that it works for them all:

![image-20231204151345301](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204151345301.png)

All solutions are contained in a class called `Solution` but with a method named after the particular problem instance. So for two-sum, the method is called `twoSum()`.

## Step4: Understand the problem

The problem given is:

> Given an array of integers `nums` and an integer `target`, return *indices of the two numbers such that they add up to `target`*.

That may sound kind of technical, and that's ok. You'll get better at reading technical sounding stuff like this when you become more familiar with programming language terms.

Let me explain with easier language.

If I handed you a list of numbers `2, 7, 11, 15`, could you find which two numbers add to 9?

If I handed you a list of numbers `3, 2, 4`, could you find which two added to 6?

What is your brain doing?

If _you_ can do it, then all that's left is instructing the computer to do it. That's what programming is!

The list of numbers is given in an array called `nums` and the number that must be added to is given in an `int` called `target`.

## Step5: Understand how your code accepts the problem

Alright, let's get a little deeper. You can maybe skim this, but it's best if the idea of method signatures was learned in the [codecademy java course](../Codecademy-Course-Learn-Java/README.md]. You can also ask a mentor for help.

Method signatures basically determine what goes into the method and what's expected to come out (returned) of the method.

You'll notice the method accepts two parameters, an array of `int` called `nums` and a single `int` called `target`. You're expected to return an array of `int`.

![image-20231204151821362](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204151821362.png)

Now we can draw a more detailed diagram of the interaction of leetcode and your code:

![image-20231204152413684](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204152413684.png)

## Step6: Try to solve this thing!

Our simple strategy will be to try to add every pair of numbers. When the sum equals the target, we'll return the two indices:

You can do this first with your fingers. Write out `2, 7, 11, 15` and see if you can place your left pointer finger and your right pointer finger on each pair of numbers.

In programming we dont' have fingers, so we use variables `i` and `j` to be our fingers. They hold the _indices_ of the input array. In this diagram, the left finger is at the 7, right finger at 11, and we add to get 18, which is not the target of 9:

![image-20231204154244236](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204154244236.png)

Paste in the following code and see if you can modify it to work. You'll be replacing the `/* replace */` with your own code.

```java
class Solution
{
    public int[] twoSum(int[] nums, int target)
    {
        int[] answer = new int[2];

        for(/* replace */; /* replace */; ++i)
        {
            for(/* replace */; /* replace */; ++j)
            {
                if (nums[i] + nums[j] == target)
                {   
                    answer[0] = i;
                    answer[1] = j;
                    return answer;
                }
            }
        }

        return answer;
    }
}
```

## Step7: Spoiler!

If you couldn't get it, don't worry! Modify the code until it looks like the screenshot below. Analyze and see why it works. If you're having trouble, grab a mentor!

![image-20231204154610752](/Users/andrewl/repos/voltage/Software-Projects-Beginner/Leetcode-2Sum-Easy/assets/image-20231204154610752.png)
