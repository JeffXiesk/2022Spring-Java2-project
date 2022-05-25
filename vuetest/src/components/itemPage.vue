<template>
  <div class="item">
    <slot>

    </slot>
  </div>
</template>

<script>
export default {
  name: "itemPage"
}
</script>

<style scoped>
.item{
  height: 5.625rem;
  border: 1px solid blue;
  margin: .25rem;
  background-color: cornflowerblue;
}
</style>