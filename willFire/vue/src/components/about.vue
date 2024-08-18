<template>
  <div>
    <!-- 两句诗 -->
    <div class="my-animation-slide-top">
      <twoPoem :isHitokoto="false"></twoPoem>

      <div id="bannerWave1"></div>
      <div id="bannerWave2"></div>
    </div>
    <div class="love-container">
      <div class="my-animation-slide-bottom">
      <div class="about-wrap">
        <h1 style="font-size: 40px;font-weight: 500;letter-spacing: 5px;">两只毛驴鸣翠柳</h1>
        <!-- 对话框 -->
        <div class="about-box">
          <h4>与 {{$store.state.webInfo.webName}} 对话中...</h4>
          <div v-if="sayShow" id="say-container"></div>
        </div>
      </div>
      <!-- 页脚 -->
      <myFooter></myFooter>
    </div>
    </div>
  </div>
</template>

<script>
  const twoPoem = () => import( "./common/twoPoem");
  const myFooter = () => import( "./common/myFooter");

  export default {
    components: {
      twoPoem,
      myFooter
    },
    data() {
      return {
        sayShow: false,
        sayContent: [
          {
            "talk": ["Hi, there👋", "欢迎来到天空之城","我是【天空之城】的开发者","你也可以叫我妙木山蛤蟆文太😃"],
            "reply": ["然后呢？ 😃"]
          }, {
            "talk": [
              "这是一个个人博客，可以查看站长的学习笔记",
              "但是不可以侵权哦😘"],
            "reply": ["这个网站有什么用吗？ 😂"]
          }, {
            "talk": ["拥有自己的独立网站难道不酷吗🚀"],
            "reply": ["你的爱好是什么呢？"]
          }, {
            "talk": ["我是计算机专业的学生", "我的爱好是唱，跳，rapper，计算机"],
            "reply": ["好了，我要去探索啦👋"]
          }, {
            "talk" : ["记得一键三连哦😘"]
          }
        ],
        sayIndex: 0
      }
    },

    computed: {},

    watch: {},

    created() {
      setTimeout(() => {
        this.sayShow = true;
        this.say();
      }, 2000);
    },

    mounted() {

    },

    methods: {
      answer(index, value) {
        $(".say-select").remove();

        let htmlStr = `<div class="say-right my-animation-slide-bottom"><span class="say-item-right">${value}</span></div>`;
        let frag = document.createRange().createContextualFragment(htmlStr);
        document.getElementById("say-container").appendChild(frag);
        if (index === 0) {
          setTimeout(() => {
            this.say();
          }, 500);
        } else {
          let htmlStr = `<div class="say-left my-animation-slide-bottom"><span class="say-item-left">👋 👋 👋</span></div>`;
          let frag = document.createRange().createContextualFragment(htmlStr);
          document.getElementById("say-container").appendChild(frag);
        }
      },
      say() {
        if (!this.$common.isEmpty(this.sayContent[this.sayIndex]) && !this.$common.isEmpty(this.sayContent[this.sayIndex].talk)) {
          this.sayContent[this.sayIndex].talk.forEach((value, index, talk) => {
            setTimeout(() => {
              let htmlStr = `<div class="say-left my-animation-slide-bottom"><span class="say-item-left">${value}</span></div>`;
              let frag = document.createRange().createContextualFragment(htmlStr);
              document.getElementById("say-container").appendChild(frag);
              if (talk.length === index + 1) {
                if (!this.$common.isEmpty(this.sayContent[this.sayIndex].reply)) {
                  setTimeout(() => {
                    if (this.sayContent[this.sayIndex].reply.length === 2) {
                      let reply0 = this.sayContent[this.sayIndex].reply[0];
                      let reply1 = this.sayContent[this.sayIndex].reply[1];
                      let htmlStr = `<div class="say-left my-animation-slide-bottom"><span class="say-select">${reply0}</span><span class="say-select">${reply1}</span></div>`;
                      let frag = document.createRange().createContextualFragment(htmlStr);
                      document.getElementById("say-container").appendChild(frag);
                      document.getElementsByClassName("say-select")[0].onclick = () => {
                        this.answer(0, reply0);
                      }
                      document.getElementsByClassName("say-select")[1].onclick = () => {
                        this.answer(1, reply1);
                      }
                    } else if (this.sayContent[this.sayIndex].reply.length === 1) {
                      let reply0 = this.sayContent[this.sayIndex].reply[0];
                      let htmlStr = `<div class="say-left my-animation-slide-bottom"><span class="say-select">${reply0}</span></div>`;
                      let frag = document.createRange().createContextualFragment(htmlStr);
                      document.getElementById("say-container").appendChild(frag);
                      document.getElementsByClassName("say-select")[0].onclick = () => {
                        this.answer(0, reply0);
                      }
                    }
                    this.sayIndex += 1;
                  }, 500);
                }
              }
            }, index * 500);
          });
        }
      }
    }
  }
</script>

<style>

  .love-container {
    background-image: linear-gradient(to right, rgba(37, 82, 110, 0.1) 1px, var(--background) 1px), linear-gradient(to bottom, rgba(37, 82, 110, 0.1) 1px, var(--background) 1px);
    background-size: 3rem 3rem;
    /*background: var(--background);*/
  }

  .about-wrap {
    text-align: center;
    width: 95%;
    max-width: 800px;
    margin: 0 auto;
    padding: 40px 20px 80px;
  }

  .about-box {
    min-height: 450px;
    padding: 5px;
    background-color: var(--maxMaxLightGray);
    border-radius: 10px;
  }

  .say-item-left {
    padding: 5px 12px;
    border-radius: 1rem;
    color: var(--maxGreyFont);
    background-color: var(--lightGray);
  }

  .say-item-right {
    padding: 5px 12px;
    border-radius: 1rem;
    color: var(--white);
    background-color: var(--translucent);
  }

  .say-left {
    display: flex;
    justify-content: left;
    margin: 15px;
  }

  .say-right {
    display: flex;
    justify-content: right;
    margin: 15px;
  }

  .say-select {
    cursor: pointer;
    background: var(--black);
    border-radius: 5px;
    padding: 5px 10px;
    margin-right: 12px;
    margin-top: 20px;
    color: var(--white);
    border: 1px solid var(--black);
  }

  .say-select:hover {
    border: 1px solid var(--themeBackground);
    color: var(--themeBackground);
    box-shadow: 0 0 5px var(--themeBackground);
  }

  #bannerWave1 {
    height: 84px;
    background: var(--bannerWave1);
    position: absolute;
    width: 200%;
    bottom: 0;
    z-index: 10;
    animation: gradientBG 120s linear infinite;
  }

  #bannerWave2 {
    height: 100px;
    background: var(--bannerWave2);
    position: absolute;
    width: 400%;
    bottom: 0;
    z-index: 5;
    animation: gradientBG 120s linear infinite;
  }
</style>
